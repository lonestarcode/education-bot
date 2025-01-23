from flask import Flask, jsonify, request, render_template
from flask_cors import CORS
import mysql.connector
from config import DB_CONFIG
import logging

app = Flask(__name__)
CORS(app)

logging.basicConfig(level=logging.DEBUG)

def get_db_connection():
    try:
        return mysql.connector.connect(**DB_CONFIG)
    except mysql.connector.Error as e:
        logging.error(f"Error connecting to database: {e}")
        return None

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/api/questions', methods=['GET'])
def get_questions():
    try:
        language = request.args.get('language', 'en')
        conn = get_db_connection()
        if not conn:
            return jsonify({"error": "Database connection failed"}), 500
        
        with conn.cursor(dictionary=True) as cursor:
            cursor.execute("SELECT id, question_text, option_a, option_b, option_c, option_d, correct_option FROM questions WHERE language = %s", (language,))
            questions = cursor.fetchall()
        
        conn.close()
        logging.debug(f"Fetched {len(questions)} questions")
        return jsonify(questions)
    except Exception as e:
        logging.error(f"Error fetching questions: {str(e)}")
        return jsonify({"error": "Failed to fetch questions"}), 500

@app.route('/api/submit_score', methods=['POST'])
def submit_score():
    try:
        data = request.json
        
        # Validate that 'username' and 'score' are present
        if not data or 'username' not in data or 'score' not in data:
            return jsonify({"error": "Missing 'username' or 'score' in request body"}), 400

        username = data['username']
        score = data['score']
        
        conn = get_db_connection()
        if not conn:
            return jsonify({"error": "Database connection failed"}), 500
        
        with conn.cursor() as cursor:
            cursor.execute("INSERT INTO leaderboard (username, score) VALUES (%s, %s)", (username, score))
            conn.commit()
        
        conn.close()
        logging.debug(f"Score submitted for {username}: {score}")
        return jsonify({"message": "Score submitted successfully"})
    except Exception as e:
        logging.error(f"Error submitting score: {str(e)}")
        return jsonify({"error": "Failed to submit score"}), 500

@app.route('/api/leaderboard', methods=['GET'])
def get_leaderboard():
    try:
        conn = get_db_connection()
        if not conn:
            return jsonify({"error": "Database connection failed"}), 500
        
        with conn.cursor(dictionary=True) as cursor:
            cursor.execute("SELECT username, score FROM leaderboard ORDER BY score DESC LIMIT 10")
            leaderboard = cursor.fetchall()
        
        conn.close()
        logging.debug(f"Fetched leaderboard with {len(leaderboard)} entries")
        return jsonify(leaderboard)
    except Exception as e:
        logging.error(f"Error fetching leaderboard: {str(e)}")
        return jsonify({"error": "Failed to fetch leaderboard"}), 500

if __name__ == '__main__':
    app.run(debug=True)