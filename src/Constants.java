import java.util.List;

public class Constants {
    //with this version, those are unmodifiable, which is what we want.
    public static final List<String> EXAM_CATEGORIES = List.of(
            "IMAGING",
            "MICROBIOLOGICAL",
            "SPECIALIZED"
    );

    public static final List<String> SPECIALTIES = List.of (
            "Cardiology",
            "Radiology",
            "Microbiology",
            "Neurology",
            "Pulmonology"
    );

    public static final List<String> MACHINE_TYPES = List.of(
            "MRI",
            "CT",
            "X-Ray"
    );

    public static final List<String> SAMPLE_TYPES = List.of(
            "Blood",
            "Urine",
            "Swab"
    );
}
