package com.edubot.config;

/**
 * Centralized API Routes Configuration.
 * This class defines constants for all API endpoints across the application.
 * It improves readability, prevents hardcoding URLs, and ensures consistency.
 */
public final class ApiRoutes {

    // ========================= BASE PATHS =========================
    public static final String BASE_API = "/api";
    public static final String STUDENTS = BASE_API + "/students";
    public static final String TEACHERS = BASE_API + "/teachers";
    public static final String MATERIALS = BASE_API + "/materials";
    public static final String LECTURES = BASE_API + "/lectures";
    public static final String RULES = BASE_API + "/rules";
    public static final String AI = BASE_API + "/ai";

    // ========================= STUDENT ROUTES =========================
    public static final String STUDENT_ADD = STUDENTS;
    public static final String STUDENT_REMOVE = STUDENTS + "/{studentId}";
    public static final String STUDENT_ACTIVITIES = STUDENTS + "/{studentId}/activities";

    // ========================= TEACHER ROUTES =========================
    public static final String TEACHER_SCHEDULE_LECTURE = TEACHERS + "/lectures/schedule";
    public static final String TEACHER_UPLOAD_MATERIAL = TEACHERS + "/study-material/upload";

    // ========================= LECTURE ROUTES =========================
    public static final String LECTURE_CREATE = LECTURES;
    public static final String LECTURE_GET_BY_ID = LECTURES + "/{lectureId}";
    public static final String LECTURE_APPLY_DEFAULT_RULES = LECTURES + "/{lectureId}/apply-default-rules";

    // ========================= RULES ROUTES =========================
    public static final String RULES_GET_DEFAULT = RULES + "/default";
    public static final String RULES_UPDATE_DEFAULT = RULES + "/default";

    // ========================= AI ROUTES =========================
    public static final String AI_SUMMARIZE = AI + "/summarize";
    public static final String AI_GENERATE_CONTENT = AI + "/generate-content";
    public static final String AI_GENERATE_QUIZ = AI + "/generate-quiz";
    public static final String AI_GENERATE_INSIGHTS = AI + "/generate-insights";

    private ApiRoutes() {
        // Prevent instantiation
    }
}