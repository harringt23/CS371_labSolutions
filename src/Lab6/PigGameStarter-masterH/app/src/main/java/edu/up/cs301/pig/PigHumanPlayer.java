package edu.up.cs301.pig;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * A GUI for a human to play Pig. This default version displays the GUI but is incomplete
 *
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    turnTotalTextView   = null;
    private TextView    messageTextView     = null;
    private ImageButton dieImageButton      = null;
    private Button      holdButton          = null;

    //Added by me to relabel "your score" and "opponent's score"
    private TextView playerScoreLabel;
    private TextView oppScoreLabel;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public PigHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //TODO You will implement this method to receive state objects from the game
        if (!(info instanceof PigGameState)) {
            super.flash(Color.RED,1000 );
        }
        else {
            if (this.playerNum == 0) {
                playerScoreTextView.setText(String.valueOf(((PigGameState) info).getPlayer0Score()));
                oppScoreTextView.setText((String.valueOf(((PigGameState) info).getPlayer1Score())));

            }

            else if (this.playerNum == 1) {
                playerScoreTextView.setText(String.valueOf(((PigGameState) info).getPlayer1Score()));
                oppScoreTextView.setText((String.valueOf(((PigGameState) info).getPlayer0Score())));

            }

            turnTotalTextView.setText(String.valueOf(((PigGameState) info).getRunningTotal()));


            //Update image of die


            switch (((PigGameState) info).getCurrDieValue()) {

                case 1:
                    dieImageButton.setImageResource(R.drawable.face1);
                    break;

                case 2:
                    dieImageButton.setImageResource(R.drawable.face2);
                    break;

                case 3:
                    dieImageButton.setImageResource(R.drawable.face3);
                    break;

                case 4:
                    dieImageButton.setImageResource(R.drawable.face4);
                    break;

                case 5:
                    dieImageButton.setImageResource(R.drawable.face5);
                    break;

                case 6:
                    dieImageButton.setImageResource(R.drawable.face6);
                    break;
            }


            //Set background color of die button
            if (((PigGameState) info).getTurnId() == 0) {
                dieImageButton.setBackgroundColor(Color.RED);
            }
            else if (((PigGameState) info).getTurnId() == 1) {
                dieImageButton.setBackgroundColor(Color.GRAY);
            }


            //Send appropriate message
            messageTextView.setText(((PigGameState) info).getMessage());

        }





    }//receiveInfo

    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {
        //TODO  You will implement this method to send appropriate action objects to the game
        if (button instanceof Button) {
            PigHoldAction holdAction= new PigHoldAction(this);
            super.game.sendAction(holdAction);
        }

        else if (button instanceof ImageButton){
            PigRollAction rollAction= new PigRollAction(this);
            super.game.sendAction(rollAction);



        }




    }// onClick

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Initialize the widget reference member variables
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreValue);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreValue);
        this.turnTotalTextView   = (TextView)activity.findViewById(R.id.turnTotalValue);
        this.messageTextView     = (TextView)activity.findViewById(R.id.messageTextView);
        this.dieImageButton      = (ImageButton)activity.findViewById(R.id.dieButton);
        this.holdButton          = (Button)activity.findViewById(R.id.holdButton);

        //Added by me to relabel "your score" and "opponent's score"
        this.playerScoreLabel = (TextView)activity.findViewById(R.id.yourScoreText);
        this.oppScoreLabel = (TextView)activity.findViewById(R.id.oppScoreText);


        //Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);

    }//setAsGui


    protected void initAfterReady() {
        this.playerScoreLabel.setText(super.name + " 's score");
        this.oppScoreLabel.setText(super.allPlayerNames[1] + " 's score");

    }
}// class PigHumanPlayer
