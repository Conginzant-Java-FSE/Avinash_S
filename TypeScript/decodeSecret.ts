function decodeSecret(message: string): string {
  const vowelMap: { [key: string]: string } = {
    "4": "a",
    "3": "e",
    "1": "i",
    "0": "o",
    "8": "u"
  };
  let result = "";
  for (let char of message) {
    if (vowelMap[char]) {
      result += vowelMap[char];
    } else {
      result += char;
    }
  }
  return result;
}
console.log(decodeSecret("H3ll0 W0rld! Th1s 1s 4 t3st."));
