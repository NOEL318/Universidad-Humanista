import { handler } from "./index.js";

const mock_id = { id: 123 };
const mockOptions = {
  name: "TestFunctionGetStudents",
  memoryLimitInMb: 128,
  getRemainingTimeinMillis: () => 2000,
};

handler(mock_id, mockOptions);
