package com.github.tonivade.resp.mvc.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ServletOutputStreamImpl extends ServletOutputStream {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

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
        output.write(b);
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
