package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;
import seedu.address.model.project.Title;

import java.util.ArrayList;
import java.util.List;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String title;
    private final String description;
    private final List<String> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("title") String title, @JsonProperty("phone") String description,
                              @JsonProperty("members") List<String> members) {
        this.title = title;
        this.description = description;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        title = source.getTitle().title;
        description = source.getDescription().description;
        members.addAll(source.getMembers());
    }

    /**
     * Converts this Jackson-friendly adapted project object into the model's {@code Project} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted project.
     */
    public Project toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        final List<String> modelPersonList = new ArrayList<>();
        for (String person : members) {
            modelPersonList.add(person);
        }

        Project project = new Project(modelTitle, modelDescription);
        project.getMembers().addAll(modelPersonList);
        return project;
    }

}
