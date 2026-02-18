function csvtoJson(csv: string): any {
  const result: any = {};
  const lines = csv.trim().split("\n");
  for (let line of lines) {
    const [key, value] = line.split(",");
    const keys = key.split(".");
    let current = result;
    keys.forEach((k, index) => {
      const isLast = index === keys.length - 1;
      const nextKey = keys[index + 1];
      if (isLast) {
        current[k] = value;
      } else {
        if (!current[k]) {
          current[k] = isNaN(Number(nextKey)) ? {} : [];
        }
        current = current[k];
      }
    });
  }
  return result;
}
const csv = `user.name.first,John
user.name.last,Doe
user.address.street,123 Main St
user.address.city,New York
user.phone.mobile,+1-555-123-4567
user.phone.work,+1-555-987-6543
tags.0,premium
tags.1,vip`;

const output = csvtoJson(csv);
console.log(output);
