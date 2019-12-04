package fr.cocoraid.oldschoolpvp.utils.ezcommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String name();
    String desc();
    String perm() default ""; //If not specified anyone can use this command
    String[] usage() default {}; //A description of what arguments this command takes
    String[] aliases() default {}; //Different names that players can run this command from

}