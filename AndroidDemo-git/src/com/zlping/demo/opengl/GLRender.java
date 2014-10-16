
package com.zlping.demo.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

public class GLRender implements Renderer {

    private Square square = new Square();

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("GLRender", "onSurfaceCreated....");
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // OpenGL docs.
        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);// OpenGL docs.
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i("GLRender", "onSurfaceChanged....");
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);// OpenGL docs.
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.
    }
    int angle = 20;
    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i("GLRender", "onDrawFrame....");
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        // Translates 10 units into the screen.
        gl.glTranslatef(0, 0, -10);
        gl.glPushMatrix();
        gl.glRotatef(angle, 0, 0, 1);
        square.draw(gl);
        
        gl.glPushMatrix();
//        gl.glLoadIdentity();
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(2, 0, -2);
        // Scale it to 50% of square A
        gl.glScalef(.5f, .5f, .5f);
        // Draw square B.
        square.draw(gl);

        gl.glPopMatrix();
        gl.glTranslatef(0, 2.5f, 0);
        // Scale it to 50% of square A
        gl.glScalef(2f, 1f, 1f);
        // Draw square B.
        square.draw(gl);
        
        // Increse the angle.
        angle=angle+5;
    }

}
