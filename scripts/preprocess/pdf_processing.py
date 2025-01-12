from flask import Flask, request, jsonify
import PyPDF2
import os

app = Flask(__name__)

# PDF Processing Routes

@app.route('/extract-text', methods=['POST'])
def extract_text():
    """
    Extract text from a PDF file.
    """
    data = request.json
    file_path = data.get('file_path')
    
    if not file_path or not os.path.exists(file_path):
        return jsonify({"error": "Invalid or missing file path"}), 400
    
    try:
        with open(file_path, 'rb') as pdf_file:
            reader = PyPDF2.PdfReader(pdf_file)
            text = ''
            for page in reader.pages:
                text += page.extract_text() or ''
        return jsonify({"text": text})
    except Exception as e:
        return jsonify({"error": f"Failed to extract text: {str(e)}"}), 500


@app.route('/metadata', methods=['POST'])
def get_metadata():
    """
    Retrieve metadata from a PDF file.
    """
    data = request.json
    file_path = data.get('file_path')
    
    if not file_path or not os.path.exists(file_path):
        return jsonify({"error": "Invalid or missing file path"}), 400
    
    try:
        with open(file_path, 'rb') as pdf_file:
            reader = PyPDF2.PdfReader(pdf_file)
            metadata = reader.metadata
            return jsonify({
                "title": metadata.title if metadata.title else "Unknown",
                "author": metadata.author if metadata.author else "Unknown",
                "created": metadata.creation_date if metadata.creation_date else "Unknown",
                "pages": len(reader.pages)
            })
    except Exception as e:
        return jsonify({"error": f"Failed to retrieve metadata: {str(e)}"}), 500


@app.route('/search-keywords', methods=['POST'])
def search_keywords():
    """
    Search for specific keywords in a PDF file.
    """
    data = request.json
    file_path = data.get('file_path')
    keywords = data.get('keywords')
    
    if not file_path or not os.path.exists(file_path) or not keywords:
        return jsonify({"error": "Invalid or missing file path or keywords"}), 400
    
    try:
        with open(file_path, 'rb') as pdf_file:
            reader = PyPDF2.PdfReader(pdf_file)
            text = ''.join([page.extract_text() or '' for page in reader.pages])
        
        found_keywords = [keyword for keyword in keywords if keyword.lower() in text.lower()]
        return jsonify({"found_keywords": found_keywords})
    except Exception as e:
        return jsonify({"error": f"Failed to search keywords: {str(e)}"}), 500


if __name__ == '__main__':
    app.run(port=5001)