package com.github.tonivade.resp.mvc;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.github.tonivade.resp.command.DefaultCommandWrapperFactory;
import com.github.tonivade.resp.command.ICommand;

public class SpringCommandWrapperFactory extends DefaultCommandWrapperFactory {

    private AutowireCapableBeanFactory factory;

    public SpringCommandWrapperFactory(AutowireCapableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public ICommand wrap(Object command) {
        factory.autowireBean(command);
        return super.wrap(command);
    }
}
