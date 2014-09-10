SwitchYard REST Interface Example
=================================

 * Default (200 or 500 HTML responses) REST interface (EAP 6.3)
 * Correctly implemented REST interface (EAP 6.3)
 * Correctly implemented REST interface (WildFly 8.1)

CURL request:
-------------

### **Authentication required:**

    Expected response: 401 Unauthorized

### **Authorization required:**

    Expected response: 403 Forbidden

### **Get all items**

 * Authentication required
 * Query params:
     * **limit** (0 - 10), default = 10
     * **offset** (min 0), default = 0
     * **completed** (true/false)
 * Response headers:
     * **X-Count**


#### **Bad limit param:**

    curl -X GET "http://localhost:8080/api/v1/todos?limit=500" -v --user authenticated:authenticated.p1

Expected response: **400 Bad Request**

    {
        "code":"400 - VALIDATION",
        "message":"Validation Error",
        "invalidFields":[
            {
                "field":"limit",
                "message":"must be between 0 and 10"
            }
        ]
    }

#### **Bad offset param:**

    curl -X GET "http://localhost:8080/api/v1/todos?offset=-20" -v --user authenticated:authenticated.p1

Expected response: **400 Bad Request**

    {
        "code":"400 - VALIDATION",
        "message":"Validation Error",
        "invalidFields":[
            {
                "field":"offset",
                "message":"must be greater than or equal to 0"
            }
        ]
    }

#### **OK:**

    curl -X GET "http://localhost:8080/api/v1/todos?limit=2" -v --user authenticated:authenticated.p1

Expected response: **200 OK, X-Count: 25**

    [
        {
            "id":1,
            "text":"Do something",
            "completed":false
        },
        {
            "id":2,
            "text":"Do something else",
            "completed":true
        }
    ]

### **Get item**

 * Authentication required

#### **Non existing item:**

    curl -X GET "http://localhost:8080/api/v1/todos/999999999" -v --user authenticated:authenticated.p1

Expected response: **404 Not Found**

#### **OK:**

    curl -X GET "http://localhost:8080/api/v1/todos/1" -v --user authenticated:authenticated.p1

Expected response: **200 OK**

    {
        "id":1,
        "text":"Do something",
        "completed":false
    }

### **Create item**

 * Authorization required
 * Response headers:
      * X-Count

#### **Bad item:**

    curl -X POST -d '{"id":"15"}' http://localhost:8080/api/v1/todos -v \
        --header "Content-Type:application/json" --user authorized:authorized.p1

Expected response: **400 Bad Request**

    {
        "code":"400 - VALIDATION",
        "message":"Validation Error",
        "invalidFields":[
            {
                "field":"id",
                "message":"must be null"
            }
        ]
    }

#### **OK:**

    curl -X POST -d '{"text":"...","completed":"true"}' http://localhost:8080/api/v1/todos -v \
        --header "Content-Type:application/json" --user authorized:authorized.p1

Expected response: **201 Created, Location: /api/v1/todos/4**

    {
        "id":4,
        "text":"...",
        "completed":true
    }

### **Update item**

 * Authorization required

#### **Bad item:**

    curl -X PUT -d '{"text":"new text"}' http://localhost:8080/api/v1/todos/4 -v \
        --header "Content-Type:application/json" --user authorized:authorized.p1

Expected response: **400 Bad Request**

    {
        "code":"400 - VALIDATION",
        "message":"Validation Error",
        "invalidFields":[
            {
                "field":"id",
                "message":"may not be null"
            }
        ]
    }

#### **Non existing item:**

    curl -X PUT -d '{"id":"99999","text":"new text"}' http://localhost:8080/api/v1/todos/99999 -v \
            --header "Content-Type:application/json" --user authorized:authorized.p1

Expected response: **404 Not Found**

#### **Inconsistent request:**

    curl -X PUT -d '{"id":"4","text":"new text"}' http://localhost:8080/api/v1/todos/5 -v \
            --header "Content-Type:application/json" --user authorized:authorized.p1

Expected response: **400 Bad Request**

    {
        "code":"400 - INCONSISTENT REQUEST",
        "message":"Entity id does not equals the requested path."
    }

#### **OK:**

    curl -X PUT -d '{"id":"4","text":"new text"}' http://localhost:8080/api/v1/todos/4 -v \
        --header "Content-Type:application/json" --user authorized:authorized.p1

Expected response: **204 No Content**

    {
        "id":4,
        "text":"...",
        "completed":true
    }

### **Delete item**

 * Authorization required

#### **Non existing item:**

Expected response: **204 No Content**

#### **OK:**

Expected response: **204 No Content**

------------
