export function getRoleColor(roleName: string) {
  if (roleName === "SUPERADMIN") {
    return "red";
  } else if (roleName === "ADMIN") {
    return "blue";
  } else if (roleName === "SUPERVISOR") {
    return "yellow";
  } else if (roleName === "SUPERVISOR") {
    return "grey";
  } else {
    return "grey";
  }
}
