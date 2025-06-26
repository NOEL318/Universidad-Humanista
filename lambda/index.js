/**
 *
 * @param {Object} event
 * @returns {Promise<void>}
 */
export const handler = async (event, context) => {
  try {
    const response = await fetch("http://74.208.133.36/student");
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    console.log("JSON recibido:", data);
    console.log("Hola Mundo", event.id);
    console.log("Function Name: ", context.name);
    console.log("Memory Limit: ", context.memoryLimitInMb + "MB");
    console.log("Memory Limit: ", context.getRemainingTimeinMillis() + "ms");
  } catch (error) {
    console.error("Error:", error);
  }
};

