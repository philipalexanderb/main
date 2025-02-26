package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTaskParser implements Parser<SortTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortTaskCommand
     * and returns a SortTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SortTaskCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
