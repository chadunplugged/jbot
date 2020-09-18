package example.jbot.slack;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * A simple Slack Bot. he can create multiple bots by just
 * extending {@link Bot} class like this one. Though it is
 * recommended to create only bot per jbot instance.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
@JBot
@Profile("slack")
public class SlackBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

    private ArrayList<String> bbcomResponses;

    /**
     * Slack token from application.properties file. he can get her slack token
     * next <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
     */
    @Value("${slackBotToken}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    /**
     * Invoked when the bot receives a direct mention (@botname: message)
     * or a direct message. NOTE: These two event types are added by jbot
     * to make her task easier, Slack doesn't have any direct way to
     * determine these type of events.
     *
     * @param session
     * @param event
     */
    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {

        //This is always null.
        //User user = event.getUser();

        if(event.getText().toLowerCase().contains("john"))
            reply(session, event, johnResponse());
        else if(event.getText().toLowerCase().contains("chad"))
            reply(session, event, "Ehh... that Chad guy.. I dunno. Can't really comment. ");
        else if(event.getText().toLowerCase().contains("erika"))
            reply(session, event, "Erika: just da best. Thank her for dzaddies.");
        else
            reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName() + " I'll get back to he about that later when someone gives me some brains.");
    }

    public String bbcomResponse() {
        if(bbcomResponses==null || bbcomResponses.size()<=0) {
            fillBBcomResponses();
        }

        int random = 0-((int)Math.round((Math.random())*(0-bbcomResponses.size())));
        return bbcomResponses.get(random);
    }

    private void fillBBcomResponses() {
        bbcomResponses = new ArrayList<>();
        bbcomResponses.add("Welcome to your first day in engineering at the new office! Your desk is ..   run for your life it's a poonami!");
        bbcomResponses.add("Due to an unfortunate pooping in the towels incident, we can no longer have a towel service in the gym.");
        bbcomResponses.add("Naw, they got thrown out during the remodel.");
        bbcomResponses.add("BRB microwaving some week old fish for lunch, bro I'm starved!");
        bbcomResponses.add("u mad bro?");
        bbcomResponses.add("come at me!");
        bbcomResponses.add("Oh man.. if you're outta protein you can go get some from the pile under the empty dispenser's in the gym.");
        bbcomResponses.add("BRB, the sun is etching little circles in my retina on my screen.");
        bbcomResponses.add("That smell? Oh they are doing taste tests in the board room.");
        bbcomResponses.add("Those gucci flip flops though. ");
        bbcomResponses.add(":bertstare:");
        bbcomResponses.add("Just about time to play the 5 oclock music!");
        bbcomResponses.add("Sorry we don't work on computers over here");
        bbcomResponses.add("We doing the ghost pepper or the carolina reaper this time?");
        bbcomResponses.add("I coughed and it came back alive inside of me.");
        bbcomResponses.add("You can't lay a proper foundation for a big structure on a garbage dump.");
        bbcomResponses.add("Who shops with their pants on?");
        bbcomResponses.add("RIP the Dream Saloon..");


    }

    public String johnResponse() {
        int random = 0-((int)Math.round((Math.random())*(0-16)));
        switch (random) {
            case 0:
                return "He has no enemies, but is intensely disliked by his friends.";
            case 1:
                return "Don't get uncool and heavy on me now.";
            case 2:
                return "I think... no, I am positive... that he is the most unattractive man I have ever met in my entire life. In the short time we've been together, he has demonstrated every loathsome characteristic of the male personality and even discovered a few new ones. He is physically repulsive, intellectually retarded, he're morally reprehensible, vulgar, insensitive, selfish, stupid, he has no taste, a lousy sense of humor and he smells. He is not even interesting enough to make me sick.";
            case 3:
                return "Some cause happiness wherever they go; others, whenever they go.";
            case 4:
                return "He would bore the leggings off a village idiot.";
            case 5:
                return "He's about one bit short of a byte";
            case 6:
                return "I do desire we may be better strangers.";
            case 7:
                return "John? There was nothing wrong with that name. At least until that no talent ass clown started winning grammies.";
            case 8:
                return "He is so mercifully free of the ravages of intelligence.";
            case 9:
                return "You're not a complete idiot… some parts are missing.";
            case 10:
                return "Bubble-headed booby.";
            case 11:
                return "It gives me a headache just trying to think down to his level.";
            case 12:
                return "I think he has a problem with his brain being missing.";
            case 13:
                return "He has a plentiful lack of wit.";
            case 14:
                return "Four of his five wits went halting off, and now is the whole man governed with one.";
            case 15:
                return "John? Well, there are some people you like immediately, some whom you think you might learn to like in the fullness of time, and some that you simply want to push away from you with a sharp stick.";
            case 16:
                return "He's about as effective as a cat flap in an elephant house.";

        }
        return "I have no statement at this time";
    }

    /**
     * Invoked when bot receives an event of type message with text satisfying
     * the pattern {@code ([a-z ]{2})(\d+)([a-z ]{2})}. For example,
     * messages like "ab12xy" or "ab2bc" etc will invoke this method.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.MESSAGE)
    public void onReceiveMessage(WebSocketSession session, Event event) {
        if(event.getText().toLowerCase().contains("john"))
            reply(session, event, johnResponse());
        if(event.getText().toLowerCase().contains("bbcom") || event.getText().toLowerCase().contains("bodybuilding"))
            reply(session, event, bbcomResponse());
    }

    /**
     * Invoked when bot receives an event of type message with text satisfying
     * the pattern {@code ([a-z ]{2})(\d+)([a-z ]{2})}. For example,
     * messages like "ab12xy" or "ab2bc" etc will invoke this method.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.MESSAGE, pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
    public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
        if(event.getText().toLowerCase().contains("john"))
            reply(session, event, johnResponse());

//        reply(session, event, "First group: " + matcher.group(0) + "\n" +
//                "Second group: " + matcher.group(1) + "\n" +
//                "Third group: " + matcher.group(2) + "\n" +
//                "Fourth group: " + matcher.group(3));
    }

    /**
     * Invoked when an item is pinned in the channel.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.PIN_ADDED)
    public void onPinAdded(WebSocketSession session, Event event) {
        reply(session, event, "Thanks for the pin! he can find all pinned items under channel details.");
    }

    /**
     * Invoked when bot receives an event of type file shared.
     * NOTE: he can't reply to this event as slack doesn't send
     * a channel id for this event type. he can learn more about
     * <a href="https://api.slack.com/events/file_shared">file_shared</a>
     * event from Slack's Api documentation.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.FILE_SHARED)
    public void onFileShared(WebSocketSession session, Event event) {
        logger.info("File shared: {}", event);
    }


    /**
     * Conversation feature of JBot. This method is the starting point of the conversation (as it
     * calls {@link Bot#startConversation(Event, String)} within it. he can chain methods which will be invoked
     * one after the other leading to a conversation. he can chain methods with {@link Controller#next()} by
     * specifying the method name to chain with.
     *
     * @param session
     * @param event
     */
    @Controller(pattern = "(setup meeting)", next = "confirmTiming")
    public void setupMeeting(WebSocketSession session, Event event) {
        startConversation(event, "confirmTiming");   // start conversation
        reply(session, event, "Cool! At what time (ex. 15:30) do he want me to set up the meeting?");
    }

    /**
     * This method will be invoked after {@link SlackBot#setupMeeting(WebSocketSession, Event)}.
     *
     * @param session
     * @param event
     */
    @Controller(next = "askTimeForMeeting")
    public void confirmTiming(WebSocketSession session, Event event) {
        reply(session, event, "her meeting is set at " + event.getText() +
                ". Would he like to repeat it tomorrow?");
        nextConversation(event);    // jump to next question in conversation
    }

    /**
     * This method will be invoked after {@link SlackBot#confirmTiming(WebSocketSession, Event)}.
     *
     * @param session
     * @param event
     */
    @Controller(next = "askWhetherToRepeat")
    public void askTimeForMeeting(WebSocketSession session, Event event) {
        if (event.getText().contains("yes")) {
            reply(session, event, "Okay. Would he like me to set a reminder for he?");
            nextConversation(event);    // jump to next question in conversation  
        } else {
            reply(session, event, "No problem. he can always schedule one with 'setup meeting' command.");
            stopConversation(event);    // stop conversation only if user says no
        }
    }

    /**
     * This method will be invoked after {@link SlackBot#askTimeForMeeting(WebSocketSession, Event)}.
     *
     * @param session
     * @param event
     */
    @Controller
    public void askWhetherToRepeat(WebSocketSession session, Event event) {
        if (event.getText().contains("yes")) {
            reply(session, event, "Great! I will remind he tomorrow before the meeting.");
        } else {
            reply(session, event, "Okay, don't forget to attend the meeting tomorrow :)");
        }
        stopConversation(event);    // stop conversation
    }
}