/**
 * 
 * @param {Object} event 
 * @returns {Promise<String>}
 */
export const handler = async (event, context) => {
    try {
        console.log("Hola Mundo", event.id);
        console.log("Function Name: ", context.name);
        console.log("Memory Limit: ", context.memoryLimitInMb + "MB");
        console.log("Memory Limit: ", context.getRemainingTimeinMillis() + "ms");

    } catch (error) {
        console.error("Error:", error);
    }
}

