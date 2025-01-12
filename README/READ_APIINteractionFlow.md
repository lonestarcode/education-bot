

🛠️ Data Flow Example (Use Case)

Scenario: A User Interacts with Study Material via API

	1.	User (via API)
	•	Initiates a request to interact with study material.

	2.	StudyMaterialController
	•	Handles the incoming request and forwards it to the service layer.

	3.	StudyMaterialService
	•	Processes the request and, if necessary, calls PDForchestration for additional operations.

	4.	PDForchestration (if required)
	•	Performs specific tasks like text extraction or AI enhancements.

	5.	StudyMaterialRepository (CRUD Operations)
	•	Interacts with the database to perform create, read, update, or delete operations.

	6.	Database
	•	Stores and manages the study_material table data.




🛠️ Data Flow Example (Use Case)

Scenario: A Student Asks a Question to the AI
	
	1.	StudentController
	•	Accepts a question from a student.
	
	2.	StudentService
	•	Validates the student ID and calls BotActivationService.
	
	3.	BotActivationService
	•	Processes the question via the AI model.
	
	4.	StudentService
	•	Logs the activity and returns the AI-generated response.


	
	
🛠️ Data Flow Example (Use Case)

Scenario: A Teacher Uploads a Study Material PDF with Summarization
	
	1.	TeacherController
	•	Receives the upload request with materialTitle, filePath, and summarize=true.
	
	2.	TeacherControlService
	•	Calls PDForchestration to extract text from the PDF.
	
	3.	PDForchestration
	•	Extracts text from the PDF using Python microservices.
	
	4.	BotActivationService
	•	Summarizes the extracted text.
	
	5.	StudyMaterialService
	•	Saves the summarized content as a StudyMaterial entry in the database.
	
	6.	Database
	•	study_material table stores the new material.
