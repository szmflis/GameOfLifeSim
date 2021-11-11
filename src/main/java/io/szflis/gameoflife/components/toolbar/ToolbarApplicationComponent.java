package io.szflis.gameoflife.components.toolbar;

import io.szflis.app.event.EventBus;
import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;

public class ToolbarApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        EventBus eventBus = applicationContext.getEventBus();
        Toolbar toolbar = new Toolbar(eventBus);
        applicationContext.getMainView().setTop(toolbar);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {

    }
}
