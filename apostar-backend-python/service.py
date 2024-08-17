from flask import Flask, jsonify, request
import requests

app = Flask(__name__)

@app.route('/loterias', methods=['GET'])
def obtener_loterias():
    url = "http://resultadosloterias.apostar.com.co/api/test-db/"

    try:
        response = requests.get(url, headers={"Accept": "application/json"}, verify=False)

        if response.status_code == 200:
            loterias = response.json()
            return jsonify(loterias), 200
        else:
            return jsonify({"error": f"Error: {response.status_code}", "message": response.text}), response.status_code
    except requests.RequestException as e:
        return jsonify({"error": "Request failed", "message": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)