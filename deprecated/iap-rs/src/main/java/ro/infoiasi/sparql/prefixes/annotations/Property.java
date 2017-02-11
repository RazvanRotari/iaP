package ro.infoiasi.sparql.prefixes.annotations;

import ro.infoiasi.sparql.prefixes.Prefix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    Prefix prefix();
    String field();
    String variableName();
}
