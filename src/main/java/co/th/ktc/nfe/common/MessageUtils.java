/**
 * 
 */
package co.th.ktc.nfe.common;

import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author temp_dev2
 *
 */
@Component
@Scope("singleton")
public class MessageUtils {
 
    public static String getMessage(String key, Object[] args) {
        try {
            ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
            bean.setBasename("messages");
            return bean.getMessage(key, args, Locale.US);
        } catch (Exception e) {
            return "Unresolved key: " + key;
        }
    }
 
    public static String getMessage(String key) {
 
        return MessageUtils.getMessage(key, null);
    }
}
