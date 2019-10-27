package seedu.address.model.timetable;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TimeTableInput {
    public TimeTable getTabletableFromFilepath(String absoluteFilepath) throws IOException, IllegalValueException {
        String content = new Scanner(new File(absoluteFilepath)).useDelimiter("\\Z").next();
        return ParserUtil.parseTimeTable(content);
    }
}
