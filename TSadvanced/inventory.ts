function filterAvailableItems<T extends { available: boolean }>(items: T[]): T[] {
  return items.filter(item => item.available);
}
const gameList = [
  { title: "FIFA 25", available: true },
  { title: "CyberRacer", available: false }
];
const result = filterAvailableItems(gameList);
console.log(result);