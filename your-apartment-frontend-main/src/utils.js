export const generateUserName = () => {
  const randomNumber = Math.floor(Math.random() * 1000);
  return `아기사자${randomNumber}`;
};