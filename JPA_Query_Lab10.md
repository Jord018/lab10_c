# CHIANG MAI UNIVERSITY
### Bachelor of Science (Software Engineering)
### College of Arts, Media and Technology
### 1st Semester / Academic Year 2026
### SE 331 Component-Based Software Development

## JPA Query

Name .................................... ID ........................

**Objective:** In this session, you will query the data using JPA Query

**Suggestion:** you should read the instructions step by step. Please try to answer a question by question without skipping some questions which you think it is extremely difficult.

**Hint:** The symbol `+` and `–` in front of the source code is to show that you have to remove the source code and add the source code only. There are not the part of the source code

---

0. Now you can remove `EventDaoImpl` from your project to make the code shorter

1. To create a query using the JPA Query,

   1.1. Create the endpoint to query the event by the `title` name as given

   **EventRepository.java**
   ```java
   public interface EventRepository extends JpaRepository<Event,Long> {
       List<Event> findAll();

   +   Page<Event> findByTitle(String title, Pageable pageRequest);
   }
   ```

   **EventDao.java**
   ```java
       Event save(Event event);
   +   Page<Event> getEvents(String name, Pageable page);
   }
   ```

   **EventDaoDbImpl.java**
   ```java
       public Event save(Event event) {
           return eventRepository.save(event);
       }
   +
   +   @Override
   +   public Page<Event> getEvents(String title, Pageable page) {
   +       return eventRepository.findByTitle(title,page);
   +   }
   }
   ```

   **EventService.java**
   ```java
       Event save(Event event);
   +   Page<Event> getEvents(String title, Pageable pageable);
   }
   ```

   **EventServiceImpl.java**
   ```java
       public Event save(Event event) {
           return eventDao.save(event);
       }
   +
   +   @Override
   +   public Page<Event> getEvents(String title, Pageable pageable) {
   +       return eventDao.getEvents(title,pageable);
   +   }
   }
   ```

   **EventController**
   ```java
       @GetMapping("events")
       public ResponseEntity<?> getEventLists(@RequestParam(value = "_limit", required = false) Integer perPage
   -           , @RequestParam(value = "_page", required = false) Integer page) {
   -       Page<Event> pageOutput = eventService.getEvents(perPage, page);
   +           , @RequestParam(value = "_page", required = false) Integer page,
   +           @RequestParam(value = "title", required = false) String title) {
   +       perPage = perPage == null ? 3 : perPage;
   +       page = page == null ? 1 : page;
   +       Page<Event> pageOutput;
   +       if (title == null) {
   +           pageOutput = eventService.getEvents(perPage,page);
   +       }else{
   +           pageOutput =
                   eventService.getEvents(title,PageRequest.of(page-1,perPage));
   +       }
           HttpHeaders responseHeader = new HttpHeaders();
           responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
           return new ResponseEntity<>(LabMapper.INSTANCE.getEventDto(pageOutput.getContent()),
               responseHeader, HttpStatus.OK);
       }
   ```

   1.2. Run the application, create a request in the ApiDog to query the data from the endpoint.

   Note that the text must be the full `title` name, and case sensitive to get the result.

   1.3. To make the search to be partial search (search for the content that contains the given word only) update the files as given

   **EventRepository.java**
   ```java
       List<Event> findAll();

   -   Page<Event> findByTitle(String title, Pageable pageRequest);
   +   Page<Event> findByTitleContaining(String title, Pageable pageRequest);
   }
   ```

   **EventDaoDbImpl.java**
   ```java
       public Page<Event> getEvents(String title, Pageable page) {
   -       return eventRepository.findByTitle(title,page);
   +       return eventRepository.findByTitleContaining(title,page);
       }
   ```

   1.4. Run the application, use only some word in the title to query the data in the database

   1.5. To query the data from `Title` or `Description` add a new query methods in the `EventRepository` as given,

   ```java
       Page<Event> findByTitleContaining(String title, Pageable pageRequest);
   +   Page<Event> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageRequest);
   }
   ```

   and in the `EventDaoImpl.java`, using the same implementation as `EventDaoDBImpl`, excepted in the get Event we use a new query

   ```java
       @Override
       public Page<Event> getEvents(String title, Pageable page) {
   -       return eventRepository.findByTitleContaining(title,page);
   +       return eventRepository.findByTitleContainingOrDescriptionContaining(title,title,page);
       }
   ```

   Setup the application to use the new implementation

   1.6. Run the application to show that we can query the text via Title, and description

   1.7. Add a new Query function, Using the method in 1.5 but change `Or` to `And`, then use the ApiDog to show the query, explain the result to the Staff

   1.8. Add Another query to get the result from the `organizer` name

   adding the **EventRepository.java**
   ```java
       Page<Event> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageRequest);
       Page<Event> findByTitleContainingAndDescriptionContaining(String title, String description, Pageable pageRequest);
   +   Page<Event> findByTitleContainingOrDescriptionContainingOrOrganizer_NameContaining(String title, String description, String organizerName, Pageable pageRequest);
   }
   ```

   update the **EventDaoImpl.java**
   ```java
       public Page<Event> getEvents(String title, Pageable page) {
   -       return eventRepository.findByTitleContainingAndDescriptionContaining(title,title,page);
   +       return eventRepository.findByTitleContainingOrDescriptionContainingOrOrganizer_NameContaining(title,title,title,page);
       }
   ```

   Show the query result to the staff

   1.9. On PostgreSQL (the course database), `findByTitleContaining` generates a case-sensitive `LIKE`, so "camt" will not match "CAMT". To search case-insensitively, add an `IgnoreCase` query method (which maps to `ILIKE` on PostgreSQL), then update the DAO and show the "camt" result to the staff.

   ```java
       Page<Event> findByTitleContainingAndDescriptionContaining(String title, String description, Pageable pageRequest);
   -   Page<Event> findByTitleContainingOrDescriptionContainingOrOrganizer_NameContaining(String title, String description, String organizerName, Pageable pageRequest);
   +   Page<Event> findByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrOrganizer_NameIgnoreCaseContaining(String title, String description, String organizerName, Pageable pageRequest);
   +
   }
   ```

   For more information about JPA Repository query in [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/reference/)

2. now we will add the search box in the `EventListView.vue`

   2.1. Open the `EventListView.vue`, import the `BaseInput` component to `EventListView` and update the code as given

   ```html
   <h1>Events For Good</h1>
   <main class="flex flex-col items-center">
     <div class="... w-64">
   +     <BaseInput
   +       v-model="keyword"
   +       type="text"
   +       label="Search..."
   +       class="w-full"/>
       </div>
   ```

   prepare the keyword to receive the data

   ```js
   +const keyword = ref('')
   </script>
   ```

   2.2. Add the `EventService.ts` to get the data using the endpoint to query from the keyword

   ```ts
     saveEvent(event) {
       return apiClient.post('/events', event)
   + },
   + getEventsByKeyword(keyword: string, perPage: number, page: number) {
   +   return apiClient.get('/events?title=' + keyword + '&_limit=' + perPage +
           '&_page=' + page)
   + }
     }
   ```

   2.3. now add the input event in the `EventListView`, the event input is propagated from the child to call the `updateKeyword`. the call of the data will be called. note that if the keyword is null, it should query all data.

   update the `EventListView.vue` as given

   ```js
   const keyword = ref('')
   +function updateKeyword() {
   +   let queryFunction;
   +   if (keyword.value === '') {
   +       queryFunction = EventService.getEvents(3, page.value)
   +   }else{
   +       queryFunction = EventService.getEventsByKeyword(keyword.value, 3, page.value)
   +   }
   +   queryFunction.then((response) => {
   +       events.value = response.data
   +       console.log('events',events.value)
   +       totalEvents.value = response.headers['x-total-count']
   +       console.log('totalEvent',totalEvents.value)
   +   }).catch(() => {
   +       router.push({ name: 'network-error-view' })
   +   })
   +}
   ```

   then update the template as given

   ```html
     <h1>Events For Good</h1>
     <main class="flex flex-col items-center">
   +   <BaseInput
   +     v-model="keyword"
   +     type="text"
   +     label="Search..."
   +     @input="updateKeyword"
   +   />
       </div>
   ```

   2.4. Now test the front end that it should be able to query the data via the search box

   2.5. Try to change the page size to 1, when we change the page, the query is not updated yet. (try to use "Kra" to search) so update the `onMounted` as given below.

   ```js
   const page = computed(() => props.page)
   onMounted(() => {
     watchEffect(() => {
   -   EventService.getEvents(1, page.value)
   -     .then((response) => {
   -       events.value = response.data
   -       totalEvents.value = response.headers['x-total-count']
   -     })
   -     .catch(() => {
   -       router.push({ name: 'network-error-view' })
   -     })
   +   updateKeyword()
     })
   })
   ```

   2.6. Show the api call in the Development console when search for something and click Next page

3. Now create a new backend project to keep the data in this class diagram in the database

   **AuctionItem** (description, type) `1` — `item` `bids` — `*` **Bid** (amount, datetime), with `0..1` `successfulBid`

   3.1. Create at least 5 AuctionItem, each of that should contains at least three Bid, three AuctionItems have successful bids.

   3.2. Create the backend endpoint to query the AuctionItem by the description.

   3.3. Create the backend endpoint to query the AuctionItem which successfulBid value is less than the specific value

   3.4. Create Front end to show list of AuctionItem

   3.5. Create the Front end to search the AuctionItem from description and type

---
*Transcribed from the Google Doc "10. JPA Query" (SE 331, Chiang Mai University) — some Vue/HTML snippet whitespace approximated from screenshots since copy/paste was disabled on the source document.*
