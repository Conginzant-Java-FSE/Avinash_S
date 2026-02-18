interface Match {
  homeTeam: string;
  awayTeam: string;
  homeScore: number;
  awayScore: number;
  stadium: string;
  matchDate: Date;
  isFinished: boolean;
}

const match1: Match = {
  homeTeam: "Brazil",
  awayTeam: "Argentina",
  homeScore: 2,
  awayScore: 1,
  stadium: "Maracana",
  matchDate: new Date(),
  isFinished: true
};

const match2: Match = {
  homeTeam: "Germany",
  awayTeam: "France",
  homeScore: 0,
  awayScore: 0,
  stadium: "Berlin Arena",
  matchDate: new Date(),
  isFinished: true
};

const match3: Match = {
  homeTeam: "Spain",
  awayTeam: "Italy",
  homeScore: 1,
  awayScore: 3,
  stadium: "Madrid Stadium",
  matchDate: new Date(),
  isFinished: false
};

function getMatchResult(match: Match): string {
  if (!match.isFinished) {
    return "Match not completed";
  }

  if (match.homeScore > match.awayScore) {
    return match.homeTeam;
  } else if (match.awayScore > match.homeScore) {
    return match.awayTeam;
  } else {
    return "Draw";
  }
}
console.log(getMatchResult(match1)); 
console.log(getMatchResult(match2)); 
console.log(getMatchResult(match3)); 
