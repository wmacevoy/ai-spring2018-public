package tictactoeopt;

class TrialWorker extends Thread {
    Parameters parameters;
    Parameters results;

    int xwins;
    int owins;
    int draws;
    int progress;

    Integer id = null;
    int id() {
	return (id != null) ? id :
	    id=(int) parameters.getLong("id");
    }

    Integer trials = null;
    int trials() {
	return (trials != null) ? trials :
	    trials=(int) parameters.getLong("trials");
    }

    Integer threads = null;
    int threads() {
	return (threads != null) ? threads :
	    threads=(int) parameters.getLong("threads");
    }

    Boolean quiet = null;
    boolean quiet() {
	return (quiet != null) ? quiet : 
	    quiet = parameters.getBoolean("quiet",true);
    }

    Integer subtrials = null;
    int subtrials() {
	return (subtrials != null) ? subtrials : 
	    subtrials = (trials()*(id()+1)/threads()-trials()*(id())/threads());
    }

    Parameters game = null;
    Parameters game() {
	return (game != null) ? game :
	    game = parameters.getParameters("game");
    }
    
    TrialWorker(Parameters _parameters) {
	parameters = _parameters;
    }
    
    public void run() {
	xwins = 0;
	owins = 0;
	draws = 0;
	log("started");
	for (int trial = 0; trial<subtrials(); ++trial) {
	    play();
	    int progress0 = (int) Math.floor(100.0*(trial+1)/subtrials());
	    if (progress != progress0 && progress0 % 10 == 0) {
		progress = progress0;
		log("" + trial + "/" + subtrials + "(" + progress + "%)"
		    + " xwins: " + xwins
		    + " owins: " + owins
		    + " draws: " + draws);
	    }
	}
	log("done");
	results=Parameters.make()
	    .set("xwins",xwins)
	    .set("owins",owins)
	    .set("draws",draws)
	    .parameters();
    }

    void progress(String msg) {
	if (!quiet()) {
	    System.out.println("worker # " + id() + ": " + msg);
	}
    }
    void play() {

	GamePlay gamePlay = new GamePlay(game());
	gamePlay.play();

	tictactoe.State state = gamePlay.getState();
	switch (state) {
	case WIN_O:
	    ++owins;
	    break;
	case WIN_X:
	    ++xwins;
	    break;
	case DRAW:
	    ++draws;
	    break;
	}
    }
}
