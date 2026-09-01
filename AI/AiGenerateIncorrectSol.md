While working on a personal MERN Stack project, I used AI to help debug an API issue. The AI suggested that the problem was related to the frontend API URL and recommended changing the API endpoint.

However, the solution was incorrect because the actual issue was related to CORS configuration on the backend. I identified this by checking the browser console and Network tab, where I found that the request was being blocked by the CORS policy.

I reviewed my Express server configuration and found that the frontend URL was not properly added to the allowed origins. I fixed the CORS configuration, tested the API again, and the request started working correctly.

This experience taught me that AI is useful for debugging and generating ideas, but I should always verify its suggestions by checking the actual error, logs, and application behavior.
