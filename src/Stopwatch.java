import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch{
    private JLabel timeLabel=new JLabel();
    private int elapsedTime=0,seconds=0,minutes=0,hours=0;
    String seconds_string=String.format("%02d",seconds);
    String minutes_string=String.format("%02d",minutes);
    String hours_string=String.format("%02d",hours);
    public Timer timer=new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime+=1000;
            hours=(elapsedTime/3600000);
            minutes=(elapsedTime/60000)%60;
            seconds=(elapsedTime/1000)%60;
            seconds_string=String.format("%02d",seconds);
            minutes_string=String.format("%02d",minutes);
            hours_string=String.format("%02d",hours);
            timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        }
    });
    Stopwatch(Frame scoreboard){
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
        timeLabel.setBounds(0,0,300,75);
        timeLabel.setFont(new Font("Verdana",Font.PLAIN,40));
        timeLabel.setBackground(Color.green);
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);
        timer.start();
        scoreboard.add(timeLabel);
    }

}

