import { useGetCurrentUserQuery } from "@/services/authApi";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { clearUser, setUser } from "./authSlice";

const AuthInitializer = ({ children }) => {
  const dispatch = useDispatch();
  const { data, isSuccess, isError, isLoading } = useGetCurrentUserQuery();
  console.log("🚀 ~ AuthInitializer ~ isError:", isError)
  console.log("🚀 ~ AuthInitializer ~ data:", data);
  console.log("🚀 ~ AuthInitializer ~ isSuccess:", isSuccess);

  useEffect(() => {
    if (isSuccess) {
      dispatch(setUser(data));
    }
    if (isError) {
      dispatch(clearUser());
    }
  }, [isError, isSuccess, data, dispatch]);

  if (isLoading) {
    return <div> Loading... </div>;
  }

  return children;
};

export default AuthInitializer;
