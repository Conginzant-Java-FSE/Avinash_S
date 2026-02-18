interface Account {
  id: number;
  email: string;
  password: string;
}
function sanitizeAccount(
  account: Account
): Omit<Account, "password"> {
  const { password, ...rest } = account;
  return rest;
}
console.log(
  sanitizeAccount({
    id: 1,
    email: "user@app.com",
    password: "secret123"
  })
);
