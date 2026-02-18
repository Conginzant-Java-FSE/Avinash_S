type Platform = "PC" | "PlayStation" | "Xbox" | "Mobile";
interface Game {
  title: string;
  price: number;
  platform: Platform;
  inStock: boolean;
}
const games: Game[] = [
  { title: "Game A", price: 50, platform: "PC", inStock: true },
  { title: "Game B", price: 60, platform: "PlayStation", inStock: true },
  { title: "Game C", price: 40, platform: "Xbox", inStock: false },
  { title: "Game D", price: 30, platform: "Mobile", inStock: true },
  { title: "Game E", price: 70, platform: "PC", inStock: true }
];
function filterByPlatform(games: Game[], platform: Platform): Game[] {
  return games.filter(game => game.platform === platform && game.inStock);
}
const pcGames = filterByPlatform(games, "PC");
console.log(pcGames);
