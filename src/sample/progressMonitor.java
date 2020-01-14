package sample;

import com.jcraft.jsch.SftpProgressMonitor;
import javafx.scene.control.ProgressBar;
import org.apache.cordova.api.CallbackContext;

// Change the class name if you want
public class progressMonitor implements SftpProgressMonitor{
    private long max                = 0;
    private long count              = 0;
    private long percent            = 0;
    private CallbackContext callbacks = null;
    ProgressBar bar = null;

    // If you need send something to the constructor, change this method
    public progressMonitor(ProgressBar b) {
        bar = b;
        bar.setProgress(0);
    }

    public void init(int op, java.lang.String src, java.lang.String dest, long max) {
        this.max = max;
        System.out.println("starting");
        System.out.println(src); // Origin destination
        System.out.println(dest); // Destination path
        System.out.println(max); // Total filesize
    }

    public boolean count(long bytes){
        this.count += bytes;

        long percentNow = this.count*100/this.max;


        if(percentNow>this.percent){
            this.percent = percentNow;
            double myPercent = (double)this.percent;
            bar.setProgress(myPercent/100);
            System.out.println("perc " + myPercent/100);
            //bar.setProgress(this.percent/100);
            //System.out.println("progress",this.percent); // Progress 0,0
            System.out.println("Max - " + max); //Total ilesize
            System.out.println("Current - " +this.count); // Progress in bytes from the total
        }

        return(true);
    }

    public void end(){
        System.out.println("finished");// The process is over
        System.out.println(this.percent); // Progress
        System.out.println(max); // Total filesize
        System.out.println(this.count); // Process in bytes from the total
    }
}