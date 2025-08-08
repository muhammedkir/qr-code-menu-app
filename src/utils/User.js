export function getLoggedInUserId() {
  return localStorage.getItem('loggedInUserId');
}

export function getAuthToken() {
  return localStorage.getItem('authToken');
}

export function logout() {
  localStorage.clear();
  window.location.href = '/';
}
