package com.github.tonivade.resp.mvc.dispatcher;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ServletOutputStreamImpl extends ServletOutputStream {

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(int b) throws IOException {
        // TODO Auto-generated method stub

    }
}
