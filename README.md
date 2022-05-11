Assignment 5

Det första som skulle göras var att en RecyclerView skulle läggas till i min layout.
Så detta gjordes i activity_main.xml genom att lägga in en RecyclerView widget som sedan gavs ett id.
Sen enligt uppgiftsbeskrivningen så skulle en ArrayList och en RecyclerViewAdapter läggas till som variabel medlemmar i min aktivitet.
Det lades även till en medlems variabel för RecyclerView.
Detta gjordes med koden:

```
MainActivity:

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Mountain> mountainArrayList;

```
Efter detta så skapades en ny layout fil som ska presentera informationen som hämtats om bergen.
I denna layout fil så lades två TextViews till och som fick varsitt id.

Efter att den nya layout filen skapats så skapades en ny java class som representerar informationen från bergen.
Två variabler skapades en för bergets namn och en för bergets storlek.
Sedan skrevs kod som returnerar informationen i funktionerna getName och getSize.
Koden ser ut som detta:

```
public class Mountain {

    private String name;
    private int size;

    public Mountain(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }


    public int getSize() {
        return size;
    }
}

```

Nu skapades en ny java class som skulle innehålla min RecyclerViewAdapter och MyViewHolder.
Först skapades klassen RecyclerViewAdapter som extendar min ViewHolder.
En medlems variabel skapas för List<Mountain> mountainList;.
Sedan för att skapa en adapter så krävs det tre funktioner onCreateViewHolder, onBindViewHolder och getItemCount.

onCreateViewHolder används för att returnera en ny ViewHolder. Används av RecyclerView när den behöver en ny ViewHolder för att kunna representera föremålet.
onBindViewHolder används av RecyclerView för att visa datan på en given position.
getItemCount används för att returnera det totala numret av föremål som hålls av adaptern.
En ViewHolder används för att beskriva hur ett föremål ska presenteras.
I min ViewHolder så skapades först två TextView variabler en för namn och en för size.
Sedan kopplades dessa med findViewById till TextView Widgetsen i min layout fil list_items som används för att presentera informationen om bergen.

```
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    List<Mountain> mountainList;

    public RecyclerViewAdapter(List<Mountain> mountainList) {
        this.mountainList = mountainList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mountainList.get(position).getName());
        holder.size.setText(String.valueOf(mountainList.get(position).getSize()));
    }

    @Override
    public int getItemCount() {
        return mountainList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView size;

        public MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
            size = view.findViewById(R.id.size);
        }
    }
}

```

Nu när RecyclerViewAdapter och ViewHolder är skapta så börjades arbetet i MainActivity.
Först så skapades en findViewById för att koppla ihop variabeln recyclerView med min RecyclerView ifrån layouten med id recycler_view.
Sedan så initierades min ArrayList med koden mountainArrayList = new ArrayList<>();.
Sen kopplades min adapter variabel ihop med java klassen för RecyclerViewAdapter samt ArrayListan med koden adapter = new RecyclerViewAdapter(mountainArrayList);.
Efter det så bestämdes det vilken typ av layout min recyclerView skulle ha och det sattes till en LinearLayout.
Och sist så kopplades min recyclerView ihop med min adapter med koden recyclerView.setAdapter(adapter);
Koden till detta ser ut såhär:

```
MainActivity:

    recyclerView= findViewById(R.id.recycler_view);
    mountainArrayList = new ArrayList<>();
    adapter = new RecyclerViewAdapter(mountainArrayList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);

```

Nu ändrades URL:en i den befintliga koden som vi fick till den URL som datan skulle hämtas ifrån i MainActivity.
Och vi gav programmet tillgång till internet genom att lägga in en kodrad i AndroidManifest.xml.

```
AndroidManifest.xml:
    <uses-permission android:name="android.permission.INTERNET" />

MainActivity:
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
```

Det som nu var kvar att göra är att ladda ner datan samt presentera den i våran recyclerView och detta gjordes i funktionen onPostExecute.
Först lades det till en kodrad i onCreate för att starta nedladdningen av JSON datan.

I onPostExecute så lades kod till för att unmarshalla JSON datan till objekt.
Först så skapas Gson objekt för att kunna unmarshalla med koden Gson gson = new Gson();
Sedan så skrevs koden Type type = new TypeToken<ArrayList<Mountain>>() {}.getType(); och ArrayList<Mountain> list = gson.fromJson(json, type) för att unmarshalla JSON datan till en lista av objekt.
mountainArrayList.addAll(list) används för att sätta in alla objekt till listan.
Och sist så användes notifyDataSetChanged för att efter uppdatering av listan låta adaptern veta att den ska uppdatera innehållet av RecyclerView.
Koden till allt detta ser ut såhär:

```
MainActivity:
    protected void onCreate(Bundle savedInstanceState) {
        new JsonTask(this).execute(JSON_URL);
        ...
    }
    @Override
    public void onPostExecute(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Mountain>>() {}.getType();
        ArrayList<Mountain> list = gson.fromJson(json, type);
        mountainArrayList.addAll(list);
        adapter.notifyDataSetChanged();
    }

```