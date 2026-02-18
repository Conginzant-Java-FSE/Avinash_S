type Patient = {
  name: string;
  urgency: number;
};
function prioritizePatients(patients: Patient[]): string[] {
  return patients
    .map((patient, index) => ({ ...patient, index }))
    .sort((a, b) => {
      if (b.urgency === a.urgency) {
        return a.index - b.index; // keep arrival order
      }
      return b.urgency - a.urgency;
    })
    .map(p => p.name);
}
const patients = [
  { name: "Alice", urgency: 8 },
  { name: "Bob", urgency: 10 },
  { name: "Carol", urgency: 8 },
  { name: "Dave", urgency: 5 },
];
console.log(prioritizePatients(patients));
