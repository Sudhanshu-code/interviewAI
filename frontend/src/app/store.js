import authReducer from "@/features/auth/authSlice";
import { baseApi } from "@/services/baseApi";
import { configureStore } from "@reduxjs/toolkit/react";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    [baseApi.reducerPath]: baseApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(baseApi.middleware),
});
