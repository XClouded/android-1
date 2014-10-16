
package com.zlping.demo.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
    // Our vertices.
    private float vertices[] = {
            -1.0f, 1.0f, -3.0f, // 0, Top Left
            -1.0f, -1.0f, 0.0f, // 1, Bottom Left
            1.0f, -1.0f, 0.0f, // 2, Bottom Right
            1.0f, 1.0f, -3.0f, // 3, Top Right
    };

    private float color[] = {
            1f, 0f, 0f, 1f, // vertex 0 red
            0f, 1f, 0f, 1f, // vertex 1 green
            0f, 0f, 1f, 1f, // vertex 2 blue
            1f, 0f, 1f, 1f, // vertex 3 magenta

    };

    // The order we like to connect them.
    private short[] indices = {
            0, 1, 2, 0, 2, 3
    };

    // Our vertex buffer.
    private FloatBuffer vertexBuffer;

    // Our index buffer.
    private ShortBuffer indexBuffer;

    private FloatBuffer colorBuffer;

    public Square() {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // short is 2 bytes, therefore we multiply the number if
        // vertices with 2.
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(color.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);
    }

    /**
     * This function draws our square on screen.
     * 
     * @param gl
     */
    public void draw(GL10 gl) {
        // gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK); // OpenGL docs

        // Enabled the vertices buffer for writing and to be used during
        // rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,// OpenGL docs
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // OpenGL docs
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE); // OpenGL docs
        
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

    }

}
