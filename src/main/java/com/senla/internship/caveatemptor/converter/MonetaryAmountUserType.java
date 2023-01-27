package com.senla.internship.caveatemptor.converter;

import com.senla.internship.caveatemptor.model.advanced.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Properties;

@NoArgsConstructor
@AllArgsConstructor
public class MonetaryAmountUserType implements CompositeUserType<MonetaryAmount>, DynamicParameterizedType {
    protected Currency convertTo;

    public String[] getPropertyNames() {
        return new String[] {"value", "currency"};
    }

//    public Type[] getPropertyTypes() {
//        return new Type[] {StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.CURRENCY};
//    }

    @Override
    public Object getPropertyValue(MonetaryAmount component, int property) throws HibernateException {
        if (property == 0) {
            return component.getValue();
        }

        return component.getCurrency();
    }

    public void setPropertyValue(Object component, int property, Object value) {
        throw new UnsupportedOperationException("MonetaryAmount is immutable");
    }

    @Override
    public MonetaryAmount instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory) {
        return null;
    }

    @Override
    public Class<?> embeddable() {
        return null;
    }

    // Возвращает адаптируемый класс
    @Override
    public Class<MonetaryAmount> returnedClass() {
        return MonetaryAmount.class;
    }

    /*
        Чтобы определить факт изменения и необходимость записи в базу данных, Hibernate
        использует проверку на равенство по значению. Можно положиться на процедуру опре-
        деления равенства, уже написанную для класса MonetaryAmount.
     */
    @Override
    public boolean equals(MonetaryAmount x, MonetaryAmount y) {
        return x == y || !(x == null || y == null) && x.equals(y);
    }

    @Override
    public int hashCode(MonetaryAmount x) {
        return x.hashCode();
    }

    /*
        Если фреймворку Hibernate потребуется скопировать значение, он вызовет этот метод.
        В случае неизменяемых классов, таких как MonetaryAmount, можно возвращать передан-
        ное значение.
     */
    @Override
    public MonetaryAmount deepCopy(MonetaryAmount value) {
        return value;
    }

    /*
        Если известно, что класс MonetaryAmount неизменяемый,
        Hibernate может применить некоторые оптимизации.
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /*
        Hiberante вызывает disassemble, когда сохраняет значение в глобальном разделяемом
        кэше второго уровня. Метод должен вернуть сериализованное значение в виде экзем-
        пляра Serializable. В случае с классом MonetaryAmount проще всего вернуть представле-
        ние в виде объекта String. Или, поскольку класс MonetaryAmount сам реализует интерфейс
        Serializable, можно вернуть его экземпляр непосредственно.
     */
    @Override
    public Serializable disassemble(MonetaryAmount value) {
        return value.toString();
    }

    /*
        Hibernate вызывает этот метод, когда читает сериализованное представление из глобаль-
        ного разделяемого кэша второго уровня. Экземпляр MonetaryAmount создается из строко-
        вого представления в виде объекта String. Если бы в кэше хранился сериализованный
        экземпляр MonetaryAmount, можно было бы вернуть его непосредственно.
     */
    @Override
    public MonetaryAmount assemble(Serializable cached, Object owner) {
        return MonetaryAmount.fromString((String) cached);
    }

    /*
        Этот метод вызывается во время операций слияния, выполняемых методом
        EntityManager#merge(). Он должен вернуть копию оригинала. Или, если
        тип-значение является неизменяемым, как MonetaryAmount, можно вернуть
        оригинал.
     */
    @Override
    public MonetaryAmount replace(MonetaryAmount detached, MonetaryAmount managed, Object owner) {
        return detached;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        /*
            Здесь можно получить доступ к некоторым динамическим параметрам, таким как имена
            столбцов, в которые производится отображение, таблицы (сущности) или даже анно-
            тации поля или метода чтения отображаемого свойства. Хотя в этом примере они и не
            потребуются.
         */
        ParameterType parameterType = (ParameterType) parameters.get(PARAMETER_TYPE);
        String[] columns = parameterType.getColumns();
        String table = parameterType.getTable();
        Annotation[] annotations = parameterType.getAnnotationsMethod();

        /*
        Для определения целевой валюты при сохранении значения в базу данных потребует-
        ся только параметр convertTo. Если параметр не установлен, по умолчанию выбираются
        доллары США.
         */
        String convertToParameter = parameters.getProperty("convertTo");
        this.convertTo = Currency.getInstance(convertToParameter != null ? convertToParameter : "USD");
    }

    /*
        Этот метод читает объект ResultSet, когда экземпляр MonetaryAmount извлекается из базы
        данных. Метод получает значения amount и currency из результата запроса и создает эк-
        земпляр класса MonetaryAmount.
     */
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws SQLException {
        BigDecimal amount = resultSet.getBigDecimal(names[0]);
        if (resultSet.wasNull()) {
            return null;
        }
        Currency currency = Currency.getInstance(resultSet.getString(names[1]));
        return new MonetaryAmount(amount, currency);
    }

    /*
        Этот метод вызывается, когда требуется сохранить объект MonetaryAmount в базу данных.
        Сумма пересчитывается в целевую валюту, и затем значения amount и currency сохраня-
        ются в экземпляре PreparedStatement (если только экземпляр MonetaryAmount не null –
        в этом случае для подготовки выражения вызывается setNull()).
     */
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor session)
            throws SQLException {
        if (value == null) {
            statement.setNull(index, StandardBasicTypes.BIG_DECIMAL.getSqlTypeCode());
            statement.setNull(index + 1, StandardBasicTypes.CURRENCY.getSqlTypeCode());
        } else {
            MonetaryAmount amount = (MonetaryAmount) value;
            MonetaryAmount dbAmount = convert(amount, convertTo);
            statement.setBigDecimal(index, dbAmount.getValue());
            statement.setString(index + 1, convertTo.getCurrencyCode());
        }
    }

    /*
        Здесь можно реализовать любые правила преобразования. Для целей примера значение
        просто удваивается, чтобы можно было легко убедиться, что преобразование прошло
        успешно. Этот код нужно поменять на настоящую конвертацию в реальном приложе-
        нии. Данный метод не является частью интерфейса UserType.
     */
    protected MonetaryAmount convert(MonetaryAmount amount, Currency toCurrency) {
        return new MonetaryAmount(amount.getValue().multiply(new BigDecimal(2)), toCurrency);
    }
}
