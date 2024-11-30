// const chatBox = document.getElementById('chat-box');
// const userInput = document.getElementById('user-input');
// const sendButton = document.getElementById('send-button');

// sendButton.addEventListener('click', sendMessage);

// function sendMessage() {
//     const userMessage = userInput.value;
//     displayMessage('User', userMessage);
//     userInput.value = '';

//     const botResponse = generateResponse(userMessage);
//     displayMessage('keshav', botResponse);
// }

// function displayMessage(sender, message) {
//     const messageElement = document.createElement('div');
//     messageElement.classList.add('message', sender.toLowerCase());
//     messageElement.textContent = `${sender}: ${message}`;
//     chatBox.appendChild(messageElement);

//     // Scroll to the bottom of the chat box
//     chatBox.scrollTop = chatBox.scrollHeight;
// }

// // function generateResponse(message) {
// //     // Simple bot response algorithm -
// //     return message;
// // }
// // Array to store predefined responses
// const responses = {
//     'hey': 'Hey!how was your day?',
//     'how are you': 'I\'m fine, how are you?',
//     'Boiboi': 'Byee Mayuri',
//     // Add more
// };


// function generateResponse(message) {
//     message = message.toLowerCase();

//     for (const key in responses) {
//         if (message.includes(key)) {
//             return responses[key];
//         }
//     }

//     return 'Sorry, I didn\'t catch that. Can you please repeat?';
// }


//above code was hardcoded



//Below code is connected to API. 

const url = 'https://cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com/v1/chat/completions';

const options = {
    method: 'POST',
    headers: {
        'x-rapidapi-key': '505b4aaee7msh2acb85fad7fb3e3p14c5bejsn80080f4ca26c',
        'x-rapidapi-host': 'cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com',
        'Content-Type': 'application/json'
    }
};

// Get references to HTML elements
const chatBox = document.getElementById('chat-box');
const userInput = document.getElementById('user-input');
const sendButton = document.getElementById('send-button');

sendButton.addEventListener('click', sendMessage);

async function sendMessage() {
    const userMessage = userInput.value;
    displayMessage('You', userMessage);
    userInput.value = ''; // Clear input field

    // Pass the user's message to the API for a response
    const botResponse = await getApiResponse(userMessage);
    displayMessage('keshav', botResponse);
}

function displayMessage(sender, message) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('message', sender.toLowerCase());
    messageElement.textContent = `${sender}: ${message}`;
    chatBox.appendChild(messageElement);

    // Scroll to the bottom of the chat box
    chatBox.scrollTop = chatBox.scrollHeight;
}

// Function to get response from the API
async function getApiResponse(userMessage) {
    // Prepare the body for API request
    const body = {
        messages: [
            {
                role: 'user',
                content: userMessage
            }
        ],
        model: 'gpt-4-turbo-2024-04-09',
        max_tokens: 100,
        temperature: 0.9
    };

    // Update the options object with the new body
    options.body = JSON.stringify(body);

    try {
        const response = await fetch(url, options);
        const data = await response.json(); // Parse the JSON response
        // If an error occurs during fetching or response is an error, handle it accordingly
        if (!response.ok) {
            throw new Error('Network response was not ok: ' + data.message);
        }
        // Extract the bot's message from the response data
        return data.choices[0].message.content || 'Sorry, no response from bot.';
    } catch (error) {
        console.error(error);
        return 'There was an error connecting to the API.';
    }
} 
