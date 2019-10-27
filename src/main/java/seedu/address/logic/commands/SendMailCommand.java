package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OwnerAccount;

//import javax.activation;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;

import static java.util.Objects.requireNonNull;

public class SendMailCommand extends Command {

    public static final String COMMAND_WORD = "sendMail";

    public static final String MESSAGE_SUCCESS = "Mail has been sent successfully";

    public static final String MESSAGE_FAILURE = "Failed to send email. Please ensure the following has been modified to your account security settings:\n"
            + "  - Enable Less secure app access\n"
            + "  - Disable the 2-Step Verification";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the project. "
            + "Parameters: "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOUNT + "alice@gmail.com"
            + PREFIX_PASSWORD + "123456"
            + PREFIX_RECIPIENT + "bob@gmail.com"
            + PREFIX_SUBJECT + "sending email"
            + PREFIX_MESSAGE + "Hello World!";

    private OwnerAccount ownerAccount;
    private String recipient;
    private String subject;
    private String message;

    public SendMailCommand(OwnerAccount ownerAccount, String recipient, String subject, String message) {
        this.ownerAccount = ownerAccount;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        //not required to checkout for sending email(?)
        //if (!model.isCheckedOut()) {
        //    throw new CommandException(model.checkoutConstrain());
        //  }

        try {
            Mailer.sendEmail(this.ownerAccount.getEmail().value, this.ownerAccount.getPassword(), this.recipient, this.subject, this.message);
            return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILURE, COMMAND_WORD);
        }
    }
}

class Mailer {
    public static void sendEmail(String from, String password, String to, String sub, String msg) throws Exception {

        final Logger logger = LogsCenter.getLogger(SendMailCommand.class);

        //Get properties object
        Properties props = new Properties();

        //for gmail server need to set this to true (need to provide authentication)
        props.put("mail.smtp.auth", "true");

        //in gmail have to proveid true value for this key
        props.put("mail.smtp.starttls.enable", "true");

        //the email host which in this case is gmail
        props.put("mail.smtp.host", "smtp.gmail.com");

        //the port is 587 for gmail
        props.put("mail.smtp.port", "587");

        //get Session => to log in using the email address
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        //compose message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(sub);
        message.setText(msg);
        //send message
        Transport.send(message); //throws an exception
    }
}

