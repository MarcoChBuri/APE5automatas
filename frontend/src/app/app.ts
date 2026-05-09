import { CommonModule } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';

import { AutomataApiService } from './services/automata-api.service';
import { AutomatonDefinition, AutomatonId, ValidationOutcome } from './models/automaton.models';

const AUTOMATA: AutomatonDefinition[] = [
  {
    id: 'tabla1',
    label: 'Tabla 1',
    description: 'Secuencias sobre el alfabeto K, G, X, F.',
    alphabet: 'KGXF',
    endpoint: '/api/automata/tabla1',
    sample: 'KGXF',
    accent: 'amber',
  },
  {
    id: 'tabla2',
    label: 'Tabla 2',
    description: 'Secuencias sobre el alfabeto H, S, C.',
    alphabet: 'HSC',
    endpoint: '/api/automata/tabla2',
    sample: 'HSC',
    accent: 'cyan',
  },
  {
    id: 'tabla3',
    label: 'Tabla 3',
    description: 'Secuencias sobre el alfabeto S, A, R.',
    alphabet: 'SAR',
    endpoint: '/api/automata/tabla3',
    sample: 'SAR',
    accent: 'violet',
  },
];

@Component({
  selector: 'app-root',
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly automata = AUTOMATA;
  protected readonly automatonId = signal<AutomatonId>('tabla1');
  protected readonly chain = signal('');

  protected readonly loading = signal(false);
  protected readonly result = signal<ValidationOutcome | null>(null);

  private readonly api = inject(AutomataApiService);

  protected readonly selectedAutomaton = computed(() => {
    return this.automata.find((automaton) => automaton.id === this.automatonId()) ?? this.automata[0];
  });

  protected readonly normalizedChain = computed(() => this.chain().trim().toUpperCase());

  protected readonly canSubmit = computed(() => {
    return this.chain().trim().length > 0 && !this.loading();
  });

  selectAutomaton(automatonId: AutomatonId): void {
    this.automatonId.set(automatonId);
    this.chain.set('');
    this.result.set(null);
  }

  appendSymbol(symbol: string): void {
    this.chain.update((value) => `${value}${symbol}`.toUpperCase());
    this.result.set(null);
  }

  backspace(): void {
    this.chain.update((value) => value.slice(0, -1));
    this.result.set(null);
  }

  clearChain(): void {
    this.chain.set('');
    this.result.set(null);
  }

  submit(): void {
    if (!this.canSubmit()) {
      return;
    }

    const automaton = this.selectedAutomaton();
    const chain = this.normalizedChain();

    this.loading.set(true);
    this.result.set(null);

    this.api.validate(automaton, chain).subscribe({
      next: (outcome) => {
        this.result.set(outcome);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      },
    });
  }

  get chainHelpMessage(): string {
    const automaton = this.selectedAutomaton();
    return `Solo se permiten los simbolos ${automaton.alphabet.split('').join(', ')}.`;
  }

  get resultTone(): 'success' | 'danger' | 'neutral' {
    if (!this.result()) {
      return 'neutral';
    }

    return this.result()!.accepted ? 'success' : 'danger';
  }
}
