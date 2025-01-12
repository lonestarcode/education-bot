from flask import Flask, request, jsonify
from transformers import pipeline

app = Flask(__name__)

# Initialize the summarization pipeline
summarizer = pipeline("summarization", model="facebook/bart-large-cnn")

@app.route('/summarize', methods=['POST'])
def summarize():
    """
    Summarize raw text.
    """
    data = request.json
    raw_text = data.get('text', '')
    
    if not raw_text.strip():
        return jsonify({"error": "No text provided for summarization"}), 400
    
    try:
        summary = summarizer(raw_text, max_length=130, min_length=30, do_sample=False)
        return jsonify({"summary": summary[0]['summary_text']})
    except Exception as e:
        return jsonify({"error": f"Summarization failed: {str(e)}"}), 500


if __name__ == '__main__':
    app.run(port=5000)