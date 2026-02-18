type Message = {
  user: string;
  text: string;
};
function groupMessages(messages: Message[]) {
  const result: { user: string; messages: string[] }[] = [];
  for (let msg of messages) {
    const lastGroup = result[result.length - 1];
    if (lastGroup && lastGroup.user === msg.user) {
      lastGroup.messages.push(msg.text);
    } else {
      result.push({
        user: msg.user,
        messages: [msg.text]
      });
    }
  }
  return result;
}
const messages = [
  { user: "Mia", text: "Hey!" },
  { user: "Mia", text: "How are you?" },
  { user: "Leo", text: "Good thanks" },
  { user: "Leo", text: "You?" },
  { user: "Mia", text: "Fine :)" },
];
console.log(groupMessages(messages));
