<script setup lang="ts">
import type { Event, Organizer } from '@/types'
import { ref, onMounted } from 'vue'
import EventService from '@/services/EventService'
import OrganizerService from '@/services/OrganizerService'
import { useRouter } from 'vue-router'
import { useMessageStore } from '@/stores/message'
import BaseInput from '@/components/BaseInput.vue'
import BaseSelect from '@/components/BaseSelect.vue'

const event = ref<Event>({
  id: null,
  category: {
    id: 0,
    name: ''
  },
  title: '',
  description: '',
  location: '',
  date: '',
  time: '',
  petsAllowed: false,
  organizer: {
    id: 0,
    name: ''
  }
})

const router = useRouter()
const store = useMessageStore()

const organizers = ref<Organizer[]>([])
onMounted(() => {
  OrganizerService.getOrganizers()
    .then((response) => {
      organizers.value = response.data
    })
    .catch(() => {
      router.push({ name: 'network-error-view' })
    })
})
function saveEvent() {
  EventService.saveEvent(event.value)
    .then((response) => {
      router.push({ name: 'event-detail-view', params: { id: response.data.id } })
      store.updateMessage('You are successfuly add a new event for' + response.data.title)
      setTimeout(() => {
        store.resetMessage()
      }, 3000)
    })
    .catch(() => {
      router.push({ name: 'network-error-view' })
    })
}
</script>

<template>
  <div>
    <h1>Create an event</h1>
    <form @submit.prevent="saveEvent">
      <h3>Name & describe your event</h3>
      <BaseInput v-model="event.category" type="text" label="Category" />

      <h3>Name & describe your event</h3>
      <BaseInput v-model="event.title" type="text" label="Title" />

      <BaseInput v-model="event.description" type="text" label="Description" />

      <h3>Where is your event?</h3>
      <BaseInput v-model="event.location" type="text" label="Location" />

      <BaseSelect v-model="event.organizer.id" :options="organizers" label="Organizer" />

      <button class="flex w-fit mx-auto items-center justify-center h-13 px-10 rounded-md font-semibold whitespace-nowrap border border-gray-400 focus:border-emerald-500 transition-all duration-200 ease-linear hover:scale-105 hover:border-emerald-500 hover:shadow-lg active:scale-100 focus:outline-none" type="submit">Submit</button>
    </form>

    <pre v-if="event.title || event.category || event.description">{{ event }}</pre>

  </div>
</template>