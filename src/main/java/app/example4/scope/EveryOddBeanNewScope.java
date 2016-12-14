package app.example4.scope;

import javafx.util.Pair;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ajoshi on 14-Dec-16.
 */
public class EveryOddBeanNewScope implements Scope {
    private Map<String, Pair<Object, Integer>> map = new HashMap<>();

    @Override
    public Object get(String beanName, ObjectFactory<?> objectFactory) {
        if (map.containsKey(beanName)) {
            Pair<Object, Integer> objectPair = map.get(beanName);
            if (objectPair.getValue() % 2 == 0) {
                map.put(beanName, new Pair(objectFactory.getObject(), map.get(beanName).getValue() + 1));
            } else {
                map.put(beanName, new Pair(map.get(beanName).getKey(), map.get(beanName).getValue() + 1));
            }
        } else {
            map.put(beanName, new Pair(objectFactory.getObject(), 1));
        }
        return map.get(beanName).getKey();
    }

    @Override
    public Object remove(String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
