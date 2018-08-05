%module(directors="1") NativeRestApi_Wrapper

/* Required include files */
%{
    #include "NativeRestApi.h"
%}

/* C++ Standard library wrappers */
%include "std_string.i"

/* Our native headers */
%include "NativeRestApi.h"

/* C++ std::function<> callback support */
%feature("director") RequestCompletion;
%inline %{
    class RequestCompletion {
    public:
        RequestCompletion() = default;

        virtual ~RequestCompletion() = default;

        /* This method will be called to in Java World by the overidden Java implementation */
        virtual void onComplete(int httpCode, const std::string& body) = 0;

        /* This method will be used from Java World to create a std::function<> object to the
         * callback in our native API
         */
        restapi::NativeRestApi::completion_t createWrapper() {
            return [this](int httpCode, const std::string& body) -> void {
                onComplete(httpCode, body);
            };
        }
    };
%}
