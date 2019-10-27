package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OwnerAccount;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

public class SignInCommand extends Command {
    private OwnerAccount ownerAccount;

    public static final String COMMAND_WORD = "signIn";

    public static final String MESSAGE_SUCCESS = "Signed In Successfully";

    public static final String MESSAGE_SIGNED_IN = "You have signed in, please log out to sign in to another account";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sign In to your Email Account. "
            + "Parameters: "
            + PREFIX_ACCOUNT + "EMAIL ACCOUNT"
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOUNT + "alice@gmail.com"
            + PREFIX_PASSWORD + "12345678";

    public SignInCommand(OwnerAccount ownerAccount) {
        requireNonNull(ownerAccount);
        this.ownerAccount = ownerAccount;
    }

    final Logger logger = LogsCenter.getLogger(SendMailCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSignedIn()) {
            throw new CommandException(MESSAGE_SIGNED_IN);
        }

        model.signIn(ownerAccount);
        return new CommandResult(String.format(MESSAGE_SUCCESS), COMMAND_WORD);
    }

}
