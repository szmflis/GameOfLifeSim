package io.szflis.gameoflife.components.infobar;

import io.szflis.gameoflife.ApplicationComponent;
import io.szflis.gameoflife.ApplicationContext;
import io.szflis.gameoflife.components.editor.EditorState;

public class InfoBarApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent(ApplicationContext applicationContext) {
        EditorState editorState = applicationContext.getStateRegistry().getState(EditorState.class);
        InfoBarViewModel viewModel = new InfoBarViewModel();

        editorState.getCursorPosition().listen(viewModel.getCursorPosition()::set);
        editorState.getDrawMode().listen(viewModel.getCurrentDrawMode()::set);

        InfoBar infoBar = new InfoBar(viewModel);
        applicationContext.getMainView().setBottom(infoBar);
    }

    @Override
    public void initState(ApplicationContext applicationContext) {

    }
}
