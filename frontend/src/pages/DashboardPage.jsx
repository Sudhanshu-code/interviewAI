import React from "react";
import { useSelector } from "react-redux";

const DashboardPage = () => {
  const { user } = useSelector((state) => state.auth);
  return (
    <div>
      <h1>Email: {user?.email}</h1>
      <h2>Role:{user?.role}</h2>
    </div>
  );
};

export default DashboardPage;
