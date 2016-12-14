package app.example4.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;

/**
 * Created by ajoshi on 14-Dec-16.
 */
public class OddScopeConfigurer extends CustomScopeConfigurer {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerScope("odd-scope", new EveryOddBeanNewScope());
        super.postProcessBeanFactory(beanFactory);
    }
}
